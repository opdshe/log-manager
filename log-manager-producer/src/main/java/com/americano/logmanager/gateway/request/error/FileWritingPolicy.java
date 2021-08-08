package com.americano.logmanager.gateway.request.error;

import com.americano.logmanager.domain.LogMessage;
import com.americano.logmanager.util.JsonConverter;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileWritingPolicy implements RequestErrorHandlePolicy {
	@Override
	public void handleMessage(MessageHandlingException failedMessage) {
		LogMessage logMessage = (LogMessage) failedMessage.getFailedMessage().getPayload();
		String jsonMessage = JsonConverter.toJson(logMessage);
		writeFailedMessage(jsonMessage);
	}

	private static void writeFailedMessage(String jsonFailedMessage) {
		File file = new File("error.txt");
		FileWriter writer = null;

		try {
			writer = new FileWriter(file, true);
			writer.write(jsonFailedMessage + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
