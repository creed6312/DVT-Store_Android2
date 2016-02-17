package com.jhb.dvt.dvt_store.Utils;

import com.google.gson.stream.JsonReader;
import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by CreeD on 2016/02/15.
 */
public class JsonRead {

    public void readJsonStream(List<Item> items, InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext())
                items.add(readItem(reader));
            reader.endArray();
        } catch (Exception es) {
            System.out.println(es.getMessage());
        } finally {
            reader.close();
        }
    }

    public Item readItem(com.google.gson.stream.JsonReader reader) throws IOException {
        Item i = new Item();
        try{
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "Id": i.setId(reader.nextString()); break;
                    case "Name": i.setName(reader.nextString()); break;
                    case "Price":  i.setPrice(reader.nextDouble()); break;
                    case "Description": i.setDetails(reader.nextString()); break;
                    case "Url": i.setImageUrl(reader.nextString()); break;
                    default: reader.skipValue(); break;
                }
            }
            reader.endObject();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }


        return i;
    }
}
