package com.sohu.mrd.videoDocId.task;
import com.sohu.mrd.videoDocId.constant.KafkaConstant;
import com.sohu.mrd.videoDocId.kafka.KafkaKit;
import com.sohu.mrd.videoDocId.kafka.VideoConsumerFunction;
/**
 * @author Jin Guopan
   @creation 2016年12月21日
 */
public class RealTimeReadKafkaTask {
	public static void main(String[] args) {
		VideoConsumerFunction videoHandler = new VideoConsumerFunction();
		KafkaKit kafkaKit = new KafkaKit();
		kafkaKit.consumerMessage(KafkaConstant.VIDEO_CONSUMER_GROUP, KafkaConstant.VIDEO_TOPIC, 10, videoHandler);
	}
}
