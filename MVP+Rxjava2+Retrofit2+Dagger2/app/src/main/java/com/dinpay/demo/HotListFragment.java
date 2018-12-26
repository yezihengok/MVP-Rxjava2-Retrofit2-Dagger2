

package com.dinpay.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinpay.demo.base.mvp.BaseFragment;
import com.dinpay.demo.bean.DataBean;
import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.contract.HotListContract;
import com.dinpay.demo.mvp.presenter.HotListPresenter;
import com.dinpay.demo.utils.CommUtil;
import com.dinpay.demo.utils.SharedUtils;
import com.dinpay.demo.utils.SnackbarUtil;
import com.dinpay.demo.utils.UilImagePresenter;
import com.dinpay.demo.view.recyclerView.BaseRecyclerHolder;
import com.dinpay.demo.view.recyclerView.BaseRecyclersAdapter;
import com.dinpay.demo.view.recyclerView.SpaceItemDecoration;
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

public class HotListFragment extends BaseFragment<HotListPresenter> implements HotListContract.HotListViewImp {

    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    //private HotListAdapter hotListAdapter;
    private BaseRecyclersAdapter<DataBean> commAdapter;
    private int hotPage =1;
    private List<DataBean> hotList; //最热列表

    @Inject
    UilImagePresenter uilImagePresenter;
    @Inject
    HotListPresenter hotListPresenter;
    int time = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.hot_main;
    }

    @Override
    public void initData(Bundle state, View parentView) {
        initview(parentView);
        //请求数据
        mPresenter.gethotList(hotPage);
    }

    @Override
    public HotListPresenter createPresenter() {
        return hotListPresenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("", this.getClass().getSimpleName() + "-onActivityCreated");
    }

    private void initview(View mView) {
        mRecyclerView = (XRecyclerView) mView.findViewById(R.id.hotrecyclerview);
        //添加上下间距
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        hotList=new ArrayList<>();
/*        hotListAdapter = new HotListAdapter(getActivity(),hotList);
        hotListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(hotListAdapter);*/

        commAdapter=new BaseRecyclersAdapter<DataBean>(getActivity(),hotList,R.layout.item_main) {
            @Override
            public void convert(BaseRecyclerHolder holder, DataBean item,final int position, View itemView) {

                TextView mTextView = holder.getView(R.id.name);
                ImageView img= holder.getView(R.id.img);


                ViewGroup.LayoutParams lp =img.getLayoutParams();
                lp.height = CommUtil.getScreenWidth()/3-CommUtil.dip2px(8);
                mTextView.setText(hotList.get(position).getName());
               img.setLayoutParams(lp);


                String url=hotList.get(position).getGifPath();
                uilImagePresenter.displayImg(img,url,CommUtil.getScreenWidth()/3);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).snackbarUtil.show(mRecyclerView,hotList.get(position).getName(), 0);
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
                //请求数据
                mPresenter.gethotList(hotPage);
            }
            @Override
            public void onLoadMore() {
                //请求数据
                mPresenter.gethotList(hotPage);
            }
        });
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void setData(NewPic newPic, int page) {
        if (newPic != null) {
            if (newPic.getData() != null) {
                if (page==0){
                    hotList.clear();
                }
                hotPage++;
                hotList.addAll(newPic.getData());
                if(hotPage==1){
                    Collections.shuffle(hotList);//随机打乱一下list顺序
                }

                Log.d("", "hotList.size():" + hotList.size());
                commAdapter.notifyDataSetChanged();
            }
            if(time==0){
                SharedUtils.putObject("hotList",newPic,getActivity());
            }
            time++;
        }
        refreshComplete(page);
    }


    private void refreshComplete(int page) {
        if(page==0){
            mRecyclerView.refreshComplete();
        }else{
            mRecyclerView.loadMoreComplete();
        }

        mRecyclerView.setRefreshProgressStyle(new Random().nextInt(28));
        mRecyclerView.setLoadingMoreProgressStyle(new Random().nextInt(28));
    }


}
