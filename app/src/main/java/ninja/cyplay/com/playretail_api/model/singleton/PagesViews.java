package ninja.cyplay.com.playretail_api.model.singleton;

import android.content.Context;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.SugarORM.PageView;

/**
 * Created by damien on 22/05/15.
 */
public class PagesViews {

    @Inject
    SellerContext sellerContext;

    @Inject
    CustomerContext customerContext;

    private Context         context;

    public PagesViews(Context context) {
        this.context = context;
        ((MVPCleanArchitectureApplication)context).inject(this);
    }

    public PageView createPageView(String page_name) {

        String sellerUsername = (sellerContext != null && sellerContext.getSeller() != null) ? sellerContext.getSeller().un : null;

        PageView pageview = null;

        if (customerContext != null && customerContext.isCustomerIdentified())
            pageview = new PageView(sellerUsername, customerContext.getCustomer().getEAN());
        else
            pageview = new PageView(sellerUsername);

        // Now
        pageview.setDatetime((double) ((System.currentTimeMillis() / 1000)));
        pageview.setPage_name(page_name);

        return pageview;
    }

    public void addPageView(PageView pageview) {
        // store with Sugar ORM
        try {
            pageview.save();
        } catch (Exception e) { }
    }

    public void addPageView(String page_name) {
        addPageView(createPageView(page_name));
    }

}
