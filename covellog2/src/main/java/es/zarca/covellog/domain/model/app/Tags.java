package es.zarca.covellog.domain.model.app;

import es.zarca.covellog.domain.model.app.exception.MalformedTagsException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@Embeddable
public class Tags implements Serializable {

    @Size(max = 255)
    @Column(name = "tags")
    private String tags;

    public Tags() {
    }

    public Tags(String tags) {
        validar(tags);
        this.tags = tags;
    }

    public String getString() {
        return tags;
    }
    
    public List<String> getTags() {
        String[] arrayTags = splitTagsString(tags);
        if (arrayTags == null) {
            return new ArrayList();
        }
        return Arrays.asList(arrayTags);
    }
    
    public boolean hasTag(String tag) {
        String[] arrayTags = splitTagsString(tags);
        for (String item : arrayTags) {
            if (item.equals(tag)) {
                return true;
            }
        }
        return false;
    }
    
    private void validar(String tags) {
        if ((tags != null) && (!tags.isEmpty())) {
            String[] arrayTags = splitTagsString(tags);
            if (arrayTags != null) {
                for (String tag : arrayTags) {
                    if (!tag.startsWith("#")) {
                        throw new MalformedTagsException(tags);
                    }
                }
            }
        }
    }
    
    private String[] splitTagsString(String tags) {
        if ((tags == null) || (tags.isEmpty())) {
            return null;
        }
        String[] result = tags.split("[\\s,]+");
        if (result == null) {
            return new String[] { tags };
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.tags);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tags other = (Tags) obj;
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }
        return true;
    }
    
}