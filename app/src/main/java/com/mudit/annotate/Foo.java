package com.mudit.annotate;

import android.support.annotation.NonNull;

import com.mudit.customanno.AutoParcel;

import java.util.Date;

/**
 * Created by mudit on 15/02/18.
 */
@AutoParcel
public class Foo {

    private Date mDate;

    public Foo(@NonNull Date date){

    }

}
