package com.github.lmg.brave.http.okhttp3;

import com.github.lmg.brave.http.internal.BraveHttpInterceptor;
import com.github.lmg.brave.http.internal.HttpRequestAdapter;
import com.github.lmg.brave.http.internal.HttpResponseAdapter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by liaomengge on 17/6/8.
 */
public class BraveOkHttp3RequestInterceptor extends BraveHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        this.clientRequestInterceptor.handle(new HttpRequestAdapter(new OkHttp3Request(builder, request), this.spanNameProvider));
        try {
            Response response = chain.proceed(builder.build());
            this.clientResponseInterceptor.handle(new HttpResponseAdapter(new OkHttp3Response(response)));
            return response;
        } catch (Exception e) {
            this.clientResponseInterceptor.handle(new HttpResponseAdapter(e));
            throw e;
        }
    }
}
