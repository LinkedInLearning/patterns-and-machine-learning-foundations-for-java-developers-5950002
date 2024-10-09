import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.ChatMessage;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] argv) {
        String apiKey = System.getenv("OPENAI_API_KEY");

        ChatLanguageModel cmodel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiChatModelName.GPT_4_O)
                .temperature(0.3)
                .timeout(Duration.ofSeconds(120))
                .maxTokens(50)
                .build();

        List<ChatMessage> messages = new ArrayList<>();

        SystemMessage sysmsg = new SystemMessage("You are a polite Java expert.");
        messages.add(sysmsg);

        UserMessage usrmsg = UserMessage.from("Why should I learn Java.");
        messages.add(usrmsg);
        
        sysmsg = new SystemMessage("Please respond in Italian");
        messages.add(sysmsg);

        var answer = cmodel.generate(messages);
        
        System.out.println(answer);
    }
}
