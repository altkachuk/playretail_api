package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;
import java.util.Map;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ACustomer;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_ATicket;

/**
 * Created by damien on 22/05/15.
 */
public interface CustomerInteractor {

    void completionFromName(String cname, final PaginatedResourceRequestCallback<String> callback);

    void searchFromName(String cname, final PaginatedResourceRequestCallback<PR_ACustomer> callback);

    void searchFromFirstNameLastNameZipCode(String firstName,
                                                   String lastName,
                                                   String zipCode,
                                                   PaginatedResourceRequestCallback<PR_ACustomer> callback);

    void search(Map<String, String> parameters, PaginatedResourceRequestCallback<PR_ACustomer> callback );

    void getUpdatedCustomers(final PaginatedResourceRequestCallback<PR_ACustomer> callback);

    void getCustomerInfo(String id, List<String> projection, final ResourceRequestCallback<PR_ACustomer> callback);

    void getCustomerInfoFromEAN(String ean, final ResourceRequestCallback<PR_ACustomer> callback);

    void updateCustomerInfo(String id, PR_ACustomer customer, final ResourceRequestCallback<PR_ACustomer> callback);

    void createCustomerInfo(PR_ACustomer customer, final ResourceRequestCallback<PR_ACustomer> callback);

    void getCustomerSalesHistory(String customerId, final PaginatedResourceRequestCallback<PR_ATicket> callback);

}
