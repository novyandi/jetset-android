package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.realm.RealmObject;
import xyz.girudo.jetset.holders.BaseHolder;
import xyz.girudo.jetset.holders.TypeHolder;

/* V = itemType, W = headerType, X = footerType*/
public abstract class BaseAdapter<V extends RealmObject, W extends RealmObject, X extends RealmObject> extends RecyclerView.Adapter<BaseHolder> {
    private static final int WITH_HEADER = 1;
    private static final int WITH_HEADER_FOOTER = 2;
    private static final int WITH_FOOTER = 3;
    protected List<V> data;
    protected List<V> originalData;
    protected Context context = null;
    private boolean withHeader;
    private boolean withFooter;

    public BaseAdapter(Context context, boolean withHeader, boolean withFooter) {
        this.context = context;
        this.withHeader = withHeader;
        this.withFooter = withFooter;
    }

    public List<V> getData() {
        return data;
    }

    public void setData(List<V> data) {
        this.data = data;
        this.originalData = data;
    }

    public V getItem(int position) {
        switch (getStyleAdapter()) {
            case WITH_HEADER:
                return data.get(position - 1);
            case WITH_HEADER_FOOTER:
                return data.get(position - 2);
            case WITH_FOOTER:
                return data.get(position - 1);
            default:
                return data.get(position);
        }
    }

    @Override
    public int getItemCount() {
        int size = data.size();
        int extra;
        switch (getStyleAdapter()) {
            case WITH_HEADER:
                extra = 1;
                break;
            case WITH_HEADER_FOOTER:
                extra = 2;
                break;
            case WITH_FOOTER:
                extra = 1;
                break;
            default:
                extra = 0;
                break;
        }
        return (data.isEmpty()) ? size : size + extra;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TypeHolder.TYPE_HEADER;
        else if (position == getItemCount() + 1)
            return TypeHolder.TYPE_FOOTER;
        else
            return TypeHolder.TYPE_ITEM;

    }

    public boolean isWithHeader() {
        return withHeader;
    }

    public boolean isWithFooter() {
        return withFooter;
    }

    private int getStyleAdapter() {
        int style = 0;
        if (isWithHeader() && isWithFooter())
            style = WITH_HEADER_FOOTER;
        else if (isWithHeader())
            style = WITH_HEADER;
        else if (isWithFooter())
            style = WITH_FOOTER;
        return style;
    }
}