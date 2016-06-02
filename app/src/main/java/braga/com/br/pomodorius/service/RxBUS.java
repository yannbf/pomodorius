package braga.com.br.pomodorius.service;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by aluno-r17 on 11/05/16.
 */

// Componente 3rd party para facilitar publish subscribe
public class RxBUS {
    private static final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public static void send(Object o) {
        _bus.onNext(o);
    }

    public static Observable<Object> toObservable() {
        return _bus;
    }
}
