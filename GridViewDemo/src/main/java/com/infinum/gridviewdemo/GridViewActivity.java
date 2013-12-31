package com.infinum.gridviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends ActionBarActivity {

    CustomItemAdapter adapter;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        grid = (GridView) findViewById(R.id.gv_grid);
        adapter = new CustomItemAdapter(this);

        grid.setAdapter(adapter);
//        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);  // ili u kodu ili u layoutu!
        grid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                // Nije dovoljno samo ovdje postaviti title. Ako samo ovdje to radimo onda ce
                // nestati titlea nakon rotacije uredjaja!
                int checkedCount = grid.getCheckedItemCount();
                actionMode.setTitle("Selected " + checkedCount);

                // Ako je selektiran samo jedan item postavi "one_item_action", inace ga ukloni (sakrij).
                MenuItem menuItem = actionMode.getMenu().findItem(R.id.one_item_action);
                if(checkedCount == 1) {
                    menuItem.setVisible(true);
                } else {
                    menuItem.setVisible(false);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.setTitle("Selected " + grid.getCheckedItemCount());

                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.grid_view_action_mode_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode (ActionMode actionMode) {
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_delete:
                        deleteSelectedItems();
                        actionMode.finish(); // Action picked, so close the CAB
                        return true;
                    case R.id.one_item_action:
                        handleOneItemAction(grid.getCheckedItemIds());
                        actionMode.finish();
                        return true;
                    case R.id.menu_select_all:
                        handleSelectAll();
                    default:
                        return false;
                }
            }

            private void handleSelectAll() {
                long[] checkedItemIds = grid.getCheckedItemIds();

                int itemCount = grid.getCount();
                for(int i = 0; i < itemCount; i++) {
                    if(!isChecked(checkedItemIds, i)) {
                        grid.performItemClick(null, i, 0);
                    }
                }
            }

            private boolean isChecked(long[] checkedItemIds, int position) {
                long itemId = adapter.getItemId(position);
                for(long checkedItemId : checkedItemIds) {
                    if(checkedItemId == itemId) return true;
                }

                return false;
            }

            private void handleOneItemAction(long[] checkedItemIds) {
                Toast.makeText(GridViewActivity.this, "Item " + checkedItemIds[0] + " selected.", Toast.LENGTH_SHORT).show();
            }

            private void deleteSelectedItems() {
                adapter.deleteItems(grid.getCheckedItemIds());
                adapter.notifyDataSetChanged();
            }

        });
    }
}
