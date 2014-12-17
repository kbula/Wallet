package com.bula.Wallet.app.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.bula.Wallet.app.Data.AllData;
import com.bula.Wallet.app.Data.WalletData;
import com.bula.Wallet.app.R;

/**
 * Created by Krzysiek on 2014-09-29.
 */
public class WalletExpandableListAdapter extends BaseExpandableListAdapter {

    Context _context;
    LayoutInflater _inflater;
    private final SparseArray<AllData> _listAllData;
    IView _view;


    public WalletExpandableListAdapter(Context context, SparseArray<AllData> listAllData ,IView view)
    {
        _context = context;
        _inflater = LayoutInflater.from(_context);
        _listAllData = listAllData;
        _view = view;
    }

    @Override
    public int getGroupCount() {
        return _listAllData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listAllData.get(groupPosition).typeData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listAllData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listAllData.get(groupPosition).typeData.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.listview_group, parent,false);
        }
        AllData group = (AllData) getGroup(groupPosition);
        Drawable img = _context.getResources().getDrawable(R.drawable.rectangle_list);

        ((CheckedTextView) convertView).setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        ((CheckedTextView) convertView).setText(group._type);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final WalletData children = (WalletData) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.listrow_details, parent, false);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children.toString());
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWalletDialog.editData(_context,children,_view);
                Toast.makeText(_context, children.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
