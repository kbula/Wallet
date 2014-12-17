package com.bula.Wallet.app.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2014-09-29.
 */
public class AllData {

    public String _type;
    public List<WalletData> typeData = new ArrayList<WalletData>();

    public AllData(String type)
    {
        _type = type;
    }
}
