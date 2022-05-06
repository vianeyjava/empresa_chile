package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private long id;
    private String title;
    private String body;
    private String topic;

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", body='" + getBody() + '\'' +
                ", topic='" + getTopic() + '\'' +
                '}';
    }

   
}
