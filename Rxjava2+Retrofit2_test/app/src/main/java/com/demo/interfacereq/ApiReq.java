package com.demo.interfacereq;

import com.demo.bean.NewPic;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 *
 * Path是网址中的参数,例如:trades/{userId}
 * Query是问号后面的参数,例如:trades/{userId}?token={token}
 * QueryMap 相当于多个@Query
 * Field用于Post请求,提交单个数据,然后要加@FormUrlEncoded
 * Body相当于多个@Field,以对象的方式提交
 * @Streaming:用于下载大文件
 * @Header,@Headers、加请求头
 * ---------------------
 * @author yzh-t105
 * @time 2018/12/19 09:24
 */
public class ApiReq {

    public interface NewListApi{

        //动态添加头
//        @POST("api/dt/item/newList.html")
//        Call<NewPic> getNewLists(@QueryMap Map<String , String> params, @HeaderMap Map<String , String> headparams);


        //添加固定的头
        @Headers({
                "ticket: yCb3B5E2dgsRQZdajvzKSM",
                "yl-os: android",
                "yl-client: fighting",
                "yl-ver: 5001200"
        })
        @POST("api/dt/item/newList.html")
        Call<NewPic> getNewList(@QueryMap Map<String , String> params);



        //添加固定的头
        @Headers({
                "ticket: yCb3B5E2dgsRQZdajvzKSM",
                "yl-os: android",
                "yl-client: fighting",
                "yl-ver: 5001200"
        })
        @GET("api/dt/tag/getByType.html")
        Call<NewPic> getByType(@Query("typeId") String typeId,@Query("pageNum") int pageNum );

    }

    // 传统方式：Call<..>接口形式
    //  RxJava 方式：Observable<..>接口形式
    public interface ByTagApi{

        //添加固定的头
        @Headers({
                "ticket: yCb3B5E2dgsRQZdajvzKSM",
                "yl-os: android",
                "yl-client: fighting",
                "yl-ver: 5001200"
        })
        @POST("api/dt/tag/{end}")
        Observable<NewPic> getByType(@Path("end") String end,@QueryMap Map<String , String> params);
    }
}
