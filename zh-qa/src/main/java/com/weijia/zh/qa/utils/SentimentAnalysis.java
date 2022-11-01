package com.weijia.zh.qa.utils;
import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 情绪分析
 * 只有积极情绪和消极情绪
 */
@Slf4j
public final  class SentimentAnalysis {

    public final static Map<String,Integer> map = new HashMap<>();
    static {
        map.put("Positive",0);
        map.put("Negative",1);

    }
    public static void main(String[] args) throws IOException, TranslateException, ModelException {
//        Classifications classifications = SentimentAnalysis.predict("It doesn't feel real. I'll have another look.");
//        Classifications.Classification best = classifications.best();
//        log.info("base:{}",best);
//        log.info("base:{}",classifications);
        System.out.println(sentimentAnalysis("你是不是傻逼啊"));
    }

    /**
     * 情绪分析
     * @param input
     * @return
     */
    public static int sentimentAnalysis(String input){
        Classifications classifications = null;
        input = FanyiV3.CHS_TO_EN(input);
        try {
            classifications = SentimentAnalysis.predict(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Classifications.Classification best = classifications.best();
        log.info("base:{}",best);
        return map.get(best.getClassName());
    }

    public static Classifications predict(String input)
            throws MalformedModelException, ModelNotFoundException, IOException,
            TranslateException {
//        String input = "fucking your mather and your father";
        log.info("input Sentence: {}", input);

        Criteria<String, Classifications> criteria =
                Criteria.builder()
                        .optApplication(Application.NLP.SENTIMENT_ANALYSIS)
                        .setTypes(String.class, Classifications.class)
                        // This model was traced on CPU and can only run on CPU
                        .optDevice(Device.cpu())
                        .optProgress(new ProgressBar())
                        .build();
        try (ZooModel<String, Classifications> model = criteria.loadModel();
             Predictor<String, Classifications> predictor = model.newPredictor()) {
            return predictor.predict(input);
        }
    }
}
