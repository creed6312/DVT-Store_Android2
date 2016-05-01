package com.jhb.dvt.dvt_store.Utils;

import android.renderscript.ScriptGroup;

import com.google.gson.stream.JsonReader;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.Models.Location;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/15.
 */
class JsonRead {

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

    public void readMapStream(List<Location> locations,InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext())
                locations.add(readLocation(reader));
            reader.endArray();
        } catch (Exception es) {
            System.out.println(es.getMessage());
        } finally {
            reader.close();
        }
    }

    public void readOrderStream(List<String> s,InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginObject();
            readOrder(reader,s);
            reader.endObject();
        } catch (Exception es) {
            System.out.println(es.getMessage());
        } finally {
            reader.close();
        }
    }

    private Location readLocation(JsonReader reader) {
        Location loc = new Location();
        try{
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "Lat": loc.setLat(reader.nextDouble()); break;
                    case "Long": loc.setLong(reader.nextDouble()); break;
                    case "Place": loc.setPlace(reader.nextString()); break;
                    case "Address": loc.setAddress(reader.nextString()); break;
                    default: reader.skipValue(); break;
                }
            }
            reader.endObject();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return loc;
    }

    private void readOrder(JsonReader reader, List<String> order)
    {
        try{
            while (reader.hasNext())
            {
                String name = reader.nextName();
                switch (name) {
                    case "Id":
                        order.add(reader.nextString());
                        break ;
                    case "Total":
                        order.add(reader.nextString());
                        break ;
                    case "Count":
                        order.add(reader.nextString());
                        break ;
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private Item readItem(JsonReader reader) {
        Item item = new Item();
        try{
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "Id": item.setId(reader.nextString()); break;
                    case "Name": item.setName(reader.nextString());  break;
                    case "Price":  item.setPrice(reader.nextDouble()); break;
                    case "Description": item.setDetails(reader.nextString()); break;
                    case "Url": item.setImageUrl(reader.nextString()); break;
                    default: reader.skipValue(); break;
                }
            }
            reader.endObject();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return item;
    }
}
