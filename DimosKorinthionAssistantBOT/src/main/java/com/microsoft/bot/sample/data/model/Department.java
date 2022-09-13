package com.microsoft.bot.sample.data.model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("departments")
public class Department {

        @Id
        private String id;
        
        private String DeptName;
        private String Description;

        public String getDeptName() {
            return this.DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }

        public String getDescription() {
            return this.Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }


     
        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public Department() {
            super();
        }

        public Department(String id) {
            super();
            this.id = id;
        }

        
        public Department(String id, String DeptName) {
            super();
            this.id = id;
            this.DeptName = DeptName;
        }
}