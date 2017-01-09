package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;

public class AsIntStream implements IntStream {
    private ArrayList<Integer> myStream;

    private AsIntStream(int... values) {
        myStream = convert(values);
    }

    private AsIntStream(ArrayList values) {
        myStream = values;
    }

    private static ArrayList<Integer> convert(int... values) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            tmp.add(values[i]);
        }
        return tmp;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }


    private void checkSize() {
        if (myStream.isEmpty()) throw new IllegalArgumentException("Stream is empty.");
    }

    @Override
    public Double average() {
        checkSize();
        return ((double) sum()) / count();
    }

    @Override
    public Integer max() {
        checkSize();
        int mx = myStream.get(0);
        for (int i = 1; i < myStream.size(); ++i) {
            if (myStream.get(i) > mx) mx = myStream.get(i);
        }
        return mx;
    }

    @Override
    public Integer min() {
        checkSize();
        int mn = myStream.get(0);
        for (int i = 1; i < myStream.size(); ++i) {
            if (myStream.get(i) < mn) mn = myStream.get(i);
        }
        return mn;
    }

    @Override
    public long count() {
        return myStream.size();
    }

    @Override
    public Integer sum() {
        int res = 0;
        for (int i : myStream) res += i;
        return res;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < myStream.size(); ++i) {
            if (predicate.test(myStream.get(i))) res.add(myStream.get(i));
        }
        return new AsIntStream(res);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < myStream.size(); ++i) action.accept(myStream.get(i));
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < myStream.size(); ++i) res.add(mapper.apply(myStream.get(i)));
        return new AsIntStream(res);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<IntStream> res = new ArrayList<>();
        for (int i = 0; i < myStream.size(); ++i) res.add(func.applyAsIntStream(myStream.get((i))));
        ArrayList<Integer> ans = new ArrayList<>();
        for (IntStream strm: res){
            for (int i : strm.toArray()){
                ans.add(i);
            }
        }
        return new AsIntStream(ans);


    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (int i : myStream) identity = op.apply(identity, i);
        return identity;
    }

    @Override
    public int[] toArray() {
        int [] res = new int[myStream.size()];
        for (int i = 0; i < res.length;++i) res[i] = myStream.get(i);
        return res;
    }

}
