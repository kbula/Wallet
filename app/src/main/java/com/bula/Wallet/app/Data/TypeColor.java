package com.bula.Wallet.app.Data;


import android.content.Context;

import com.bula.Wallet.app.R;

/**
 * Created by Krzysiek on 2014-09-02.
 */
public enum TypeColor {

    TEEST{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_eat);
        }
    },
    EAT{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_eat);
        }
    },
    TRANSPORT{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_transport);
        }
    },
    FLAT{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_flat);
        }
    },
    HEALTHY{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_healthy);
        }
    },
    CLOTHES{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_clothes);
        }
    },
    RELAX{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_relax);
        }
    },
    OTHER{
        public int getColor(Context context)
        {
            return context.getResources().getColor(R.color.color_other);
        }
    };

    abstract public int getColor(Context context);

}
