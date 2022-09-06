package ru.yandex.praktikum.api.model;
import java.util.ArrayList;
import java.util.List;

public class OrderList {
        public Integer id;
        public Integer courierId;
        public String firstName;
        public String lastName;
        public String address;
        public String metroStation;
        public String phone;
        public Integer rentTime;
        public String deliveryDate;
        public Integer track;
        public List<String> color = new ArrayList<>();
        public String comment;
        public String createdAt;
        public String updatedAt;
        public Integer status;
}
