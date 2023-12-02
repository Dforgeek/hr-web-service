from transformers import AutoTokenizer, AutoModel
import torch

class SBERT:
    def __init__(self, is_gpu=False):
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        self.tokenizer = AutoTokenizer.from_pretrained("ai-forever/sbert_large_mt_nlu_ru")
        self.model = AutoModel.from_pretrained("ai-forever/sbert_large_mt_nlu_ru")

    @staticmethod
    def mean_pooling(model_output, attention_mask):
        token_embeddings = model_output[0]  # First element of model_output contains all token embeddings
        input_mask_expanded = attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
        sum_embeddings = torch.sum(token_embeddings * input_mask_expanded, 1)
        sum_mask = torch.clamp(input_mask_expanded.sum(1), min=1e-9)
        return sum_embeddings / sum_mask

    def encode(self, sentence):

        encoded_input = self.tokenizer([sentence], padding=True, truncation=True, max_length=128,
                                       return_tensors='pt').to(self.device)
        with torch.no_grad():
            model_output = self.model(**encoded_input)
        sentence_embedding = SBERT.mean_pooling(model_output, encoded_input['attention_mask'])
        return sentence_embedding



