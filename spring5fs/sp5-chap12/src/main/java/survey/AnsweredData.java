package survey;

import java.util.List;

public class AnsweredData {
	
	private List<String> responses;
	private Respondent res;
	
	public List<String> getResponses() {
		return responses;		// name="responses[i]"(jsp)
	}
	
	public void setResponses(List<String> responses) {
		this.responses = responses;		// name="responses[i]"(jsp)
	}
	
	public Respondent getRes() {
		return res;
	}
	
	public void setRes(Respondent res) {
		this.res = res;
	}

}
