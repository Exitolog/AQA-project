package by.sergey.belyakov.api.issues;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class IssueRequestDto {

	String header;
	String description;

}
