package com.ncurilovic.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


/*
*  Tutorials:
*  1.) http://code.tutsplus.com/tutorials/getting-started-with-reactivex-on-android--cms-24387
*  2.) http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/
*
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // examples
        rxDefault();
        rxDefaultSimple();
        rxDefaultMoreSimple();
        rxOperatos();
        rxOperatosMap();
        rxHandlingAsynchronousJobs();
        rxHandlingEvents();
    }

    private void rxDefault() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("rx", s);
            }

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }
        };

        myObservable.subscribe(mySubscriber);
    }

    private void rxDefaultSimple() {
        Observable<String> myObservable = Observable.just("Hello world");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("rx", "simple->" + s);
            }
        };

        myObservable.subscribe(onNextAction);
    }

    private void rxDefaultMoreSimple() {
        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("rx", "more simple->" + s);
                    }
                });
    }

    private void rxOperatos() {
        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}); // Emits each item of the array, one at a time

        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d("rx", "operators->" + String.valueOf(i)); // Prints the number received
            }
        });

    }

    private void rxOperatosMap() {
        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}); // Emits each item of the array, one at a time

        myArrayObservable = myArrayObservable.map(new Func1<Integer, Integer>() { // Input and Output are both Integer
            @Override
            public Integer call(Integer integer) {
                return integer * integer; // Square the number
            }
        });

        myArrayObservable
                .skip(2) // Skip the first two items
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) { // Ignores any item that returns false
                        return integer % 2 == 0;
                    }
                });


    }

    private void rxHandlingEvents() {
        /* Button myButton = (Button)findViewById(R.id.my_button); // Create a Button from a layout

        Observable<OnClickEvent> clicksObservable = ViewObservable.clicks(myButton); // Create a ViewObservable for the Button

        clicksObservable
                .skip(4)    // Skip the first 4 clicks
                .subscribe(new Action1<OnClickEvent>() {
                    @Override
                    public void call(OnClickEvent onClickEvent) {
                        Log.d("Click Action", "Clicked!");
                        // Printed from the fifth click onwards
                    }
                }); */

    }

    private void rxHandlingAsynchronousJobs() {
        /*Observable<String> fetchFromGoogle = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = fetchData("http://www.google.com");
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                }catch(Exception e){
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        fetchFromGoogle
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.setText(view.getText() + "\n" + s); // Change a View
                    }
                });

        fetchFromGoogle = fetchFromGoogle.subscribeOn(Schedulers.newThread());
        fetchFromYahoo = fetchFromYahoo.subscribeOn(Schedulers.newThread());

        // Fetch from both simultaneously
        Observable<String> zipped
                = Observable.zip(fetchFromGoogle, fetchFromYahoo, new Func2<String, String, String>() {
            @Override
            public String call(String google, String yahoo) {
                // Do something with the results of both threads
                return google + "\n" + yahoo;
            }
        });

        Observable<String> concatenated = Observable.concat(fetchFromGoogle, fetchFromYahoo);
        // Emit the results one after another*/
    }

}
