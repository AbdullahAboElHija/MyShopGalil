package com.example.myshop.Data

import android.os.Parcel
import android.os.Parcelable

class Item(
    //to add item id
    val itemID:String?,
    val itemName: String?,
    val itemPrice: String?,
    val itemDescription:String?,
    val itemPhoto: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    constructor():this("","","","","")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemName)
        parcel.writeString(itemPrice)
        parcel.writeString(itemDescription)
        parcel.writeString(itemPhoto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
