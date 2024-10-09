import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;

public class GetEmbedding {
    public static void main(String[] argv) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        
        EmbeddingModel model = OpenAiEmbeddingModel.withApiKey(apiKey);

        Response<Embedding> response = model.embed("One fish, two fish, red fish, blue fish");
        Embedding embedding = response.content();

        System.out.println(embedding);
    }
}
