package com.paralect.datasource.retrofit;

import android.content.Context;

import com.paralect.datasourceretrofit.R;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Oleg Tarashkevich on 09/02/2018.
 */

public class RetrofitException extends RuntimeException {

    public static String getMessage(Context context, Throwable e) {
        String message = "";
        if (e instanceof RetrofitException) {
            RetrofitException ex = (RetrofitException) e;
            try {
                switch (ex.getKind()){
                    case HTTP:
                        // TODO: Here can be any response object
//                        InstagramResponse ob = ex.getErrorBodyAs(InstagramResponse.class);
//                        message = ob.getFriendlyMessage();
                        message = e.getMessage();
                        break;
                    case NETWORK:
                        message = context.getString(R.string.error_unreachable);
                        break;
                    case UNEXPECTED:
                        message = context.getString(R.string.error_unknown);
                        break;
                }
            } catch (Throwable error){
                message = e.getMessage();
            }
        } else {
            message = e.getMessage();
        }
        return message;
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, null, retrofit);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;

    public RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    @Override
    public String toString() {
        return "RetrofitException{" +
                "message='" + getMessage() + '\'' +
                "url='" + url + '\'' +
                ", kind=" + kind +
                '}';
    }
}