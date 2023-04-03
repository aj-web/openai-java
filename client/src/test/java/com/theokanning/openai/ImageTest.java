package com.theokanning.openai;

import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.CreateImageVariationRequest;
import com.theokanning.openai.image.Image;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ImageTest {

    static String filePath = "src/test/resources/penguin.png";
    static String fileWithAlphaPath = "src/test/resources/penguin_with_alpha.png";
    static String maskPath = "src/test/resources/mask.png";

//    String token = System.getenv("OPENAI_TOKEN");
    String token = "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";
    OpenAiService service = new OpenAiService(token, 30);


    @Test
    void createImageUrl() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("Let's say you're a senior game artist and help out with a drawing of Johnny Silver's hand for the game Cyberpunk 2077")
                .n(3)
                .size("256x256")
                .user("user")
                .build();

        List<Image> images = service.createImage(createImageRequest).getData();
        assertEquals(3, images.size());
        if (images.size()>0){
            images.forEach(e-> System.out.println(e.getUrl()));
        }
    }

    @Test
    void createImageBase64() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("penguin")
                .responseFormat("b64_json")
                .user("testing")
                .build();

        List<Image> images = service.createImage(createImageRequest).getData();
        assertEquals(1, images.size());
        assertNotNull(images.get(0).getB64Json());
    }

    @Test
    void createImageEdit() {
        CreateImageEditRequest createImageRequest = CreateImageEditRequest.builder()
                .prompt("a penguin with a red background")
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageEdit(createImageRequest, fileWithAlphaPath, null).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }

    @Test
    void createImageEditWithMask() {
        CreateImageEditRequest createImageRequest = CreateImageEditRequest.builder()
                .prompt("a penguin with a red hat")
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageEdit(createImageRequest, filePath, maskPath).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }

    @Test
    void createImageVariation() {
        CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageVariation(createImageVariationRequest, filePath).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }
}
