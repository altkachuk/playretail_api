package ninja.cyplay.com.playretail_api.model.business;

import org.parceler.Parcel;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ARecommendation;

@Parcel
public class Recommendation extends PR_ARecommendation {

    public List<Product> rl;
    public List<Segment> segments;
    public List<Product> rsl;

    /*
    public Integer rc;
    public Integer rsc;

    public List<Integer> getRl_ids() {

        List<Integer> result = new ArrayList<Integer>();
        if(rl != null) {
            for (Iterator<Product> i = rl.iterator(); i.hasNext(); ) {
                Product item = i.next();
                result.add(item.getSkid());
            }
        }
        return result;
    }
    */

}
