package ge.rodichev.eventgenerator.dto;

import lombok.*;

@Getter
@Setter
public class Factory{
    private long id;
    private Resource producedResourceType;
    private Double generationEfficiency;
}
