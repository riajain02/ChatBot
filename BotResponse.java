import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import opennlp.tools.sentdetect.SentenceDetectorME; 
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME; 
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.postag.POSModel; 
import opennlp.tools.postag.POSSample; 
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.cmdline.parser.ParserTool; 
import opennlp.tools.parser.Parse; 
import opennlp.tools.parser.Parser; 
import opennlp.tools.parser.ParserFactory; 
import opennlp.tools.parser.ParserModel;
import opennlp.tools.chunker.ChunkerME; 
import opennlp.tools.chunker.ChunkerModel; 
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.util.Span;
import opennlp.tools.util.InputStreamFactory;
//import java.io.ObjectStreamClass;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;

public class BotResponse
{
    public static void main(String args[]) throws Exception { 
        String text = "Books about AI to read";
        // Sentence detection
        String[] sents = detectSentences(text);
        // Tokenization
        String[] tokens = tokenize(text);
        // Name Entity Recognition
        //Span[][] entities = recognizeEntities(tokens);
        // POS Tagging
        String[] posTags = posTag(tokens);
        // Lemmatization
        String[] lemmas = lemmatize(tokens, posTags);
        // Train custom categorizing model
        DoccatModel model = trainCategorizerModel();
        // Categorizing/Chunking
        String category = categorize(model, lemmas);
        
        // Print statements
        System.out.println(text);
        for (String sentence : sents) System.out.print("  |  " + sentence);
        System.out.println("\n");
        for (String token : tokens) System.out.print("  |  " + token);
        System.out.println("\n");
       /*for (Span[] entity : entities)
        {
            for (Span object : entity) System.out.print("  |  " + object.toString() + "  " + tokens[object.getStart()]);
            if (entity.length > 0) System.out.println("\n");
        }
        System.out.println("\n");*/
        for (String tag : posTags) System.out.print("  |  " + tag);
        System.out.println("\n");
       // for (Parse p : parses) p.show();
        //System.out.println("\n");
        //for (String s : chunks) System.out.print("  |  " + s);
    }

    public static String[] detectSentences(String sentence) throws Exception
    {   
        // Load en-sent.bin model (sentence detector model)
        InputStream input = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-sent.bin");
        // Instantiate SentenceModel.class
        SentenceModel model = new SentenceModel(input);
        // Instantiate SentenceDetectorME.class, which splits text into sentences
        SentenceDetectorME detector = new SentenceDetectorME(model);
        // Detect sentences from text using sentDetect() method
        String sentences[] = detector.sentDetect(sentence);
        return sentences;
    }
    
    public static String[] tokenize(String text) throws Exception
    {
        // Load en-token.bin model (tokenizer model)
        InputStream input = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-token.bin");
        // Instantiate TokenizerModel class, the model for tokenization
        TokenizerModel model = new TokenizerModel(input);
        // Instantiate TokenizerME class, which tokenizes the sentences
        TokenizerME tokenizer = new TokenizerME(model);
        // Tokenize each sentence of input
        String[] tokens = tokenizer.tokenize(text);
        return tokens;
    }

    public static Span[][] recognizeEntities(String[] words) throws Exception
    {
        // Load en-ner-[entity].bin models for dates, locations, organizations, people, money, percentages, and times
        InputStream inputDate = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-date.bin");
        InputStream inputLoc = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-location.bin");
        InputStream inputOrg = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-organization.bin");
        InputStream inputPer = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-person.bin");
        InputStream inputMon = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-money.bin");
        InputStream inputPerc = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-percentage.bin");
        InputStream inputTime = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-ner-time.bin");
        // Instantiate TokenNameFinderModel.class, the model for identifying each type of entity
        TokenNameFinderModel modelDate = new TokenNameFinderModel(inputDate);
        TokenNameFinderModel modelLoc = new TokenNameFinderModel(inputLoc);
        TokenNameFinderModel modelOrg = new TokenNameFinderModel(inputOrg);
        TokenNameFinderModel modelPer = new TokenNameFinderModel(inputPer);
        TokenNameFinderModel modelMon = new TokenNameFinderModel(inputMon);
        TokenNameFinderModel modelPerc = new TokenNameFinderModel(inputPerc);
        TokenNameFinderModel modelTime = new TokenNameFinderModel(inputTime);
        // Instantiate NameFinder.class for each entity, which will identify each occurence of the entity in text
        NameFinderME nameFinderDate = new NameFinderME(modelDate);
        NameFinderME nameFinderLoc = new NameFinderME(modelLoc);
        NameFinderME nameFinderOrg = new NameFinderME(modelOrg);
        NameFinderME nameFinderPer = new NameFinderME(modelPer);
        NameFinderME nameFinderMon = new NameFinderME(modelMon);
        NameFinderME nameFinderPerc = new NameFinderME(modelPerc);
        NameFinderME nameFinderTime = new NameFinderME(modelTime);
        // Find occurences of each entity in the text
        Span[][] entities = new Span[7][];
        entities[0] = nameFinderDate.find(words);
        entities[1] = nameFinderLoc.find(words);
        entities[2] = nameFinderOrg.find(words);
        entities[3] = nameFinderPer.find(words);
        entities[4] = nameFinderMon.find(words);
        entities[5] = nameFinderPerc.find(words);
        entities[6] = nameFinderTime.find(words);
        return entities;
    }

    public static String[] posTag(String[] tokens) throws Exception
    {
        /*        
         * For reference:
         * NN - noun, NNP - proper noun
         * DT - determinder
         * VB, VBD, VBZ - verb (base, past tense, singular present, respectively)
         * IN - preposition/conjunction
         * TO - to
         * JJ - adjective
        */
        // Load en-pos-maxent.bin model to tag each token with a part of speech (pos)       
        InputStream input = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-pos-maxent.bin"); 
        // Instantiate POSModel.class, the model for identifying the pos
        POSModel model = new POSModel(input); 
        // Instantiate POSTaggerME.class, which will tag each token with a pos
        POSTaggerME tagger = new POSTaggerME(model); 
        // Generate pos tags using the tag() method
        String[] tags = tagger.tag(tokens);
        return tags;
        //Instantiating the POSSample class 
        // POSSample sample = new POSSample(tokens, tags); 
        //System.out.println(sample.toString());
    }

    public static String[] lemmatize(String[] tokens, String[] posTags) throws Exception
    {
        InputStream input = new FileInputStream("C:/Users/rohit.ROHITLAPTOP/CSA/ChatBot/model/en-lemmatizer.bin");
        LemmatizerModel model = new LemmatizerModel(input);
        LemmatizerME categorizer = new LemmatizerME(model);
        String[] lemmaTokens = categorizer.lemmatize(tokens, posTags);    
        return lemmaTokens;
    }

    public static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
        // faq-categorizer.txt is a custom training data with categories as per our chat
        // requirements.
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("categories.txt"));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
     
        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });
     
        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 0);
     
        // Train a model with classifications from above file.
        DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
        return model;
    }

    public static String categorize(DoccatModel model, String[] tokens) throws IOException
    {
        // Instantiate DocumentCategorizer.class, the document categorizer tool
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
     
        // Get best possible category.
        double[] probabilitiesOfOutcomes = myCategorizer.categorize(tokens);
        String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
        System.out.println("Category: " + category);
     
        return category; 
    }

    public static String findCategory(String text) throws Exception
    {
        // Sentence detection
        String[] sents = detectSentences(text);
        // Tokenization
        String[] tokens = tokenize(text);
        // Name Entity Recognition
        //Span[][] entities = recognizeEntities(tokens);
        // POS Tagging
        String[] posTags = posTag(tokens);
        // Lemmatization
        String[] lemmas = lemmatize(tokens, posTags);
        // Train custom categorizing model
        DoccatModel model = trainCategorizerModel();
        // Categorizing/Chunking
        String category = categorize(model, lemmas);
        return category;
    }
}