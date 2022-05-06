package com.app.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TopicDTO {
	private long topic_id;
	private String topic;
	private List<String> deviceTokens;

	@Override
	public String toString() {
		return "TopicDTO{" + "topic_id=" + getTopic_id() + ", topic='" + getTopic() + '\'' + ", deviceTokens="
				+ getDeviceTokens() + '}';
	}

}
