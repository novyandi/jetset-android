package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 2/7/17
 */

public abstract class SelectableAdapter<V extends RealmObject, W extends RealmObject, X extends RealmObject> extends BaseAdapter<V, W, X> {
    @SuppressWarnings("unused")
    private static final String TAG = SelectableAdapter.class.getSimpleName();

    private SparseBooleanArray selectedItems;
    private boolean singleSelection;
    private int lastSelectionPosition;

    public SelectableAdapter(Context context, boolean withHeader, boolean withFooter) {
        super(context, withHeader, withFooter);
        selectedItems = new SparseBooleanArray();
        singleSelection = false; //default is multiple selection
    }

    public boolean isSingleSelection() {
        return singleSelection;
    }

    public void setSingleSelection(boolean singleSelection) {
        this.singleSelection = singleSelection;
    }

    public int getLastSelectionPosition() {
        return lastSelectionPosition;
    }

    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position) {
        if (isSingleSelection()) clearSelection(); // executed only if single selection mode
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        lastSelectionPosition = position;
        notifyItemChanged(position);
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }
}