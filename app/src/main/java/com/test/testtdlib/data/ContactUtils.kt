package com.test.testtdlib.data

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log

class ContactUtils private constructor() {

    companion object {
        fun readContacts(contentResolver: ContentResolver): List<ContactShortInfo> {
            val contacts: MutableList<ContactShortInfo> = ArrayList()
            var cursor: Cursor? = null
            try {
                cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        val hasPhone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        Log.d("Test", "has phone number: $hasPhone")
                        if (Integer.valueOf(hasPhone) == 1) {
                            val contactID =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                            val firstLastName = getFirstLastName(contentResolver, contactID)
                            Log.d(
                                "Test",
                                "full name: ${firstLastName.first} ${firstLastName.second}"
                            )
                            val phoneNumber = readPhonesNumber(contentResolver, contactID)
                            Log.d("Test", "phone number: $phoneNumber")
                            contacts.add(ContactShortInfo(
                                firstLastName.first,
                                firstLastName.second,
                                phoneNumber
                            ))
                        }
                    } while (cursor.moveToNext())
                }

            } finally {
                cursor?.close()
            }
            return contacts
        }

        private fun readPhonesNumber(contentResolver: ContentResolver, contactID: String): String {
            var numbers: Cursor? = null
            var phoneNumber = ""
            try {
                numbers = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    "${ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID} = ?",
                    arrayOf(contactID),
                    null
                )
                if (numbers != null && numbers.moveToFirst()) {
                    do {
                        phoneNumber =
                            numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    } while (numbers.moveToNext())
                }
            } finally {
                numbers?.close()
            }
            return phoneNumber
        }


        private fun getFirstLastName(
            contentResolver: ContentResolver,
            contactID: String
        ): Pair<String, String> {
            var firstLastName = Pair("", "")
            var cursor: Cursor? = null
            try {
                cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
                    arrayOf(
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                        contactID
                    ),
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val firstName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
                            ?: ""
                    val lastName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))
                            ?: ""
                    firstLastName = Pair(firstName, lastName)
                }
            } finally {
                cursor?.close()
            }
            return firstLastName
        }
    }
}