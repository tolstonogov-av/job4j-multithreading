package ru.job4j.caches;

public class Base {

   private final int id;

   private int version;

   private String name;

   public Base(int id, String name) {
      this.id = id;
      this.name = name;
      this.version = 1;
   }

   public int getId() {
      return id;
   }

   public void setVersion(int version) {
      this.version = version;
   }

   public int getVersion() {
      return version;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
