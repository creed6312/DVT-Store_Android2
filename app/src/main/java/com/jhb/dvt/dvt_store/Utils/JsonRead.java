package com.jhb.dvt.dvt_store.Utils;

import android.os.Message;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSardinha on 2016-02-12.
 */
public class JsonRead {
    List<Item> items ;

    public List<Item> readJsonStream(List<Item> items, InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        this.items = items ;
        try {
            return readItemArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Item> readItemArray(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            items.add(readItem(reader));
        }
        reader.endArray();
        return items;
    }

    public Item readItem(JsonReader reader) throws IOException {
        Item i = new Item();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Id")) {
                i.setId(reader.nextString());
            } else if (name.equals("Name")) {
                i.setName(reader.nextString());
            } else if (name.equals("Price")) {
                i.setPrice(reader.nextDouble());
            } else if (name.equals("Description")) {
                i.setDetails(reader.nextString());
            } else if (name.equals("Url")) {
                i.setImageUrl(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return i;
    }
}
