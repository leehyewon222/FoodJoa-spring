package Common;

import org.springframework.stereotype.Component;

@Component
public class CommonVO {

	private float averageRating;
	private int reviewCount;
	
	public CommonVO() {
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
}