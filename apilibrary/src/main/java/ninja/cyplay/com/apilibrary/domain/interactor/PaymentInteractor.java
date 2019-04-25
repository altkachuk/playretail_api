package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentTransaction;

/**
 * Created by damien on 29/06/15.
 */
public interface PaymentInteractor {

    void getCustomerPayments(String customerId,
                             List<String> project,
                             PaginatedResourceRequestCallback<PR_APayment> callback);

    void getCustomerPayment(String customerId,
                            String paymentId,
                            final ResourceRequestCallback<PR_APayment> callback);

    void addCustomerPayment(String customerId,
                            PR_APayment pr_A_payment,
                            final ResourceRequestCallback<PR_APayment> callback);

    void updateCustomerPayment(String customerId,
                               String paymentId,
                               PR_APayment pr_aPayment,
                               final ResourceRequestCallback<PR_APayment > callback);

    void addTransactionToCustomerPayment(String customerId,
                                         String paymentId,
                                         PR_APaymentTransaction transaction,
                                         final ResourceRequestCallback<PR_APaymentTransaction> callback);

    void updateCustomerTransactionPayment(String customerId,
                                          String paymentId,
                                          String transactionId,
                                          PR_APaymentTransaction pr_aPaymentTransaction,
                                          final ResourceRequestCallback<PR_APaymentTransaction> callback);



}
