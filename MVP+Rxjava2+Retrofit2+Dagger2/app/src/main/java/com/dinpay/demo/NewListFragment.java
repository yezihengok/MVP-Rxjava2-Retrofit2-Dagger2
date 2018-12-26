

package com.dinpay.demo;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinpay.demo.base.mvp.BaseFragment;
import com.dinpay.demo.bean.DataBean;
import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.contract.NewsListContract;
import com.dinpay.demo.mvp.presenter.NewsListPresenter;
import com.dinpay.demo.utils.CommUtil;
import com.dinpay.demo.utils.SharedUtils;
import com.dinpay.demo.utils.SnackbarUtil;
import com.dinpay.demo.utils.UilImagePresenter;
import com.dinpay.demo.view.recyclerView.BaseRecyclerHolder;
import com.dinpay.demo.view.recyclerView.BaseRecyclersAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by yzh on 2016/09/25.
 */
public class NewListFragment extends BaseFragment<NewsListPresenter> implements NewsListContract.NewsListViewImp {

    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    //private newListAdapter newListAdapter;
    private BaseRecyclersAdapter<DataBean> commAdapter;
    private int hotPage = 1;
    private List<DataBean> newList; //最热列表


    @Inject
    UilImagePresenter uilImagePresenter;
    @Inject
    NewsListPresenter newsListPresenter;
    int time = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.hot_main;
    }

    @Override
    public void initData(Bundle state, View parentView) {
        initview(parentView);
        //请求数据
        mPresenter.getNewsList(hotPage);
    }

    @Override
    public NewsListPresenter createPresenter() {
       // return new NewsListPresenter();
        return newsListPresenter;
    }



    private void initview(View mView) {
        mRecyclerView = (XRecyclerView) mView.findViewById(R.id.hotrecyclerview);
        // mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        newList = new ArrayList<>();
/*        newListAdapter = new newListAdapter(getActivity(),newList);
        newListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(newListAdapter);*/

        commAdapter = new BaseRecyclersAdapter<DataBean>(getActivity(), newList, R.layout.item_main) {
            @Override
            public void convert(BaseRecyclerHolder holder, DataBean item, final int position, View itemView) {

                TextView mTextView = holder.getView(R.id.name);
                ImageView img = holder.getView(R.id.img);


                ViewGroup.LayoutParams lp = img.getLayoutParams();
                lp.height = CommUtil.getScreenWidth() / 3 - CommUtil.dip2px(8);
                mTextView.setText(newList.get(position).getName());
                img.setLayoutParams(lp);

                String url = newList.get(position).getGifPath();
                uilImagePresenter.displayImg(img, url, CommUtil.getScreenWidth() / 3);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).snackbarUtil.show(mRecyclerView, newList.get(position).getName(), 0);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commAdapter);
        configRecyclerView();


    }

    private void configRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                hotPage = 0;
                mPresenter.getNewsList(hotPage);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getNewsList(hotPage);
            }
        });
        mRecyclerView.setRefreshing(true);
    }




    @Override
    public void setData(NewPic newPic, int page) {
        if (newPic != null) {
            if (newPic.getData() != null) {
                if (page == 0) {
                    newList.clear();
                }
                hotPage++;
                newList.addAll(newPic.getData());
                if (hotPage == 1) {
                    Collections.shuffle(newList);//随机打乱一下list顺序
                }

                Log.d("", "newList.size():" + newList.size());
                commAdapter.notifyDataSetChanged();
            }
            if (time == 0) {
                SharedUtils.putObject("newList", newPic, getActivity());
            }
            time++;
        }
        refreshComplete(page);
    }


    private void refreshComplete(int page) {
        if (page == 0) {
            mRecyclerView.refreshComplete();
        } else {
            mRecyclerView.loadMoreComplete();
        }
        mRecyclerView.setRefreshProgressStyle(new Random().nextInt(28));
        mRecyclerView.setLoadingMoreProgressStyle(new Random().nextInt(28));
    }


}