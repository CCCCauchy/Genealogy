package com.oraro.genealogy.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Administrator on 2016/10/13.
 */
@Database(name = GenealogyDatabase.NAME, version = GenealogyDatabase.VERSION, foreignKeysSupported = true)
public class GenealogyDatabase {
    public static final String NAME = "Genealogy";

    public static final int VERSION = 1;
}
