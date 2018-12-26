package com.dinpay.demo.view.recyclerView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yzh-t105
 * 2018-10-10 18:49:14
 * 公用的RecyclerView适配器(集成添加 HeaderView FooterView)
 *
 */
public abstract class BaseRecyclersAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    private Context context;//上下文
    private List<T> list;//数据源
    private LayoutInflater inflater;//布局器
    private int itemLayoutId;//布局id
  //  private boolean isScrolling;//是否在滚动
    private OnItemClickListener listener;//点击事件监听器
    private OnItemLongClickListener longClickListener;//长按监听器
    private RecyclerView recyclerView;




    //在RecyclerView提供数据的时候调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView parent, View view, int position);
    }

    public void setLists(List<T> lists)
    {
        if(lists==null){
            lists=new ArrayList<>();
        }
        this.list = lists;
        notifyDataSetChanged();
    }
    /**
     * 插入一项
     *
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一项
     *
     * @param position 删除位置
     */
    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public BaseRecyclersAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);

    }

//    @Override
//    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(itemLayoutId, parent, false);
//        return BaseRecyclerHolder.getRecyclerHolder(context, view);
//    }



//
//
//
//    @Override
//    public void onBindViewHolder(final BaseRecyclerHolder holder, int position) {
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null && view != null && recyclerView != null) {
//                    int position = recyclerView.getChildAdapterPosition(view);
//                    listener.onItemClick(recyclerView, view, position);
//                }
//            }
//        });
//
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                if (longClickListener != null && view != null && recyclerView != null) {
//                    int position = recyclerView.getChildAdapterPosition(view);
//                    longClickListener.onItemLongClick(recyclerView, view, position);
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        convert(holder, list.get(position), position,holder.itemView);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list == null ? 0 : list.size();
//    }


    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return BaseRecyclerHolder.getRecyclerHolder(context, mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return BaseRecyclerHolder.getRecyclerHolder(context, mFooterView);
        }
        View view = inflater.inflate(itemLayoutId, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
       // JLog.v("onBindViewHolder--------"+getItemViewType(position)+"------position:"+position);

        if(getItemViewType(position) == TYPE_NORMAL){

            //如果设置了HeaderView position 需要-1 因为position==0已经被header占用了
            if(mHeaderView == null){
                convert(holder, list.get(position), position,holder.itemView);
            }else{
                convert(holder, list.get(position-1), position-1,holder.itemView);
            }

            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            //这里加载数据的时候要注意，是从position -1开始，因为position==0已经被header占用了

            return;
        }else if(getItemViewType(position) == TYPE_FOOTER){
          //  convert(holder, list.get(position), position,holder.itemView);
            return;
        }


    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return list==null?0: list.size();
        }else if(mHeaderView == null && mFooterView != null){
            return list==null?0: list.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return list==null?0: list.size() + 1;
        }else {
            return list==null?0: list.size() + 2;
        }
    }


//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
//        this.longClickListener = longClickListener;
//    }

    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param holder      ViewHolder
     * @param item        子项
     * @param position    位置
     */
    public abstract void convert(BaseRecyclerHolder holder, T item, int position,View itemView);






    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private View mHeaderView;
    private View mFooterView;
    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setHeaderView(int itemLayoutId) {
        mHeaderView = inflater.inflate(itemLayoutId, recyclerView, false);
        notifyItemInserted(0);
    }
    public void setFooterView(int itemLayoutId) {
        mFooterView  = inflater.inflate(itemLayoutId, recyclerView, false);
        notifyItemInserted(getItemCount()-1);
    }
    public void setHeaderView(View v) {
        mHeaderView = v;
        notifyItemInserted(0);
    }
    public void setFooterView(View v) {
        mFooterView  = v;
        notifyItemInserted(getItemCount()-1);
    }


    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (mHeaderView != null &&position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (mFooterView != null &&position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
}
