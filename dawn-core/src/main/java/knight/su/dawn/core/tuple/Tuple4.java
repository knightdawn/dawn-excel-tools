package knight.su.dawn.core.tuple;

/**
 * 定义一个四元组
 * @author Edward
 * @date 2018年8月31日
 * @version 1.0
 */
public class Tuple4<T1, T2, T3, T4> {


    public T1 _1;
    public T2 _2;
    public T3 _3;
    public T4 _4;
    
    public Tuple4() {}
    
    public Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    @Override
    public String toString() {
        return "Tuple4 [_1=" + _1 + ", _2=" + _2 + ", _3=" + _3 + ", _4=" + _4 + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
        result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
        result = prime * result + ((_3 == null) ? 0 : _3.hashCode());
        result = prime * result + ((_4 == null) ? 0 : _4.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tuple4<?, ?, ?, ?> other = (Tuple4<?, ?, ?, ?>) obj;
        if (_1 == null) {
            if (other._1 != null)
                return false;
        } else if (!_1.equals(other._1))
            return false;
        if (_2 == null) {
            if (other._2 != null)
                return false;
        } else if (!_2.equals(other._2))
            return false;
        if (_3 == null) {
            if (other._3 != null)
                return false;
        } else if (!_3.equals(other._3))
            return false;
        if (_4 == null) {
            if (other._4 != null)
                return false;
        } else if (!_4.equals(other._4))
            return false;
        return true;
    }

}
