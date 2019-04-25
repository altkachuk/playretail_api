package ninja.cyplay.com.apilibrary.domain.interactor;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.PaginatedResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.interactor.callback.ResourceRequestCallback;
import ninja.cyplay.com.apilibrary.domain.repository.PlayRetailRepository;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.exception.BaseException;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.requests.ResourceRequest;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.PaginatedResourcesResponse;
import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ResourceResponse;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APayment;
import ninja.cyplay.com.apilibrary.models.abstractmodels.PR_APaymentTransaction;
import ninja.cyplay.com.apilibrary.utils.LogUtils;

/**
 * Created by damien on 29/06/15.
 */
public class PaymentInteractorImpl extends AbstractInteractor implements PaymentInteractor {

    private PlayRetailRepository repository;
    private Context context;

    public PaymentInteractorImpl(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor, PlayRetailRepository repository) {
        super(interactorExecutor, mainThreadExecutor);
        this.context = context;
        this.repository = repository;
    }


    @Override
    public void getCustomerPayments(String customerId, List<String> project, PaginatedResourceRequestCallback<PR_APayment> callback) {
        final WeakReference<ResourceRequest> getPaymentsRequest = new WeakReference<ResourceRequest>(new ResourceRequest().id(customerId).project(project));
        final WeakReference<PaginatedResourceRequestCallback<PR_APayment>> getPaymentsRequestCallback = new WeakReference<PaginatedResourceRequestCallback<PR_APayment>>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                PaymentInteractorImpl.this.doGetPaginatedResource(getPaymentsRequest.get(),
                        getPaymentsRequestCallback.get(),
                        new PaginatedResourceGetter() {
                            @Override
                            public PaginatedResourcesResponse getPaginatedResource(ResourceRequest resourceRequest) {
                                return repository.getCustomerPayments(getPaymentsRequest.get());
                            }
                        }
                );
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    // CUSTOMER PAYMENT GET
    // -------------------------------------------------------------------------------------------
    @Override
    public void getCustomerPayment(String customerId,
                                   String paymentId,
                                   final ResourceRequestCallback<PR_APayment> callback) {
        final WeakReference<ResourceRequest<PR_APayment>> addCustomerPaymentRequest = new WeakReference<ResourceRequest<PR_APayment>>(new ResourceRequest<>().id(customerId).secondLevelId(paymentId));
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                PaymentInteractorImpl.super.doGetResource(addCustomerPaymentRequest.get(),
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.getCustomerPayment(addCustomerPaymentRequest.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    // CUSTOMER PAYMENT CREATION
    // -------------------------------------------------------------------------------------------

    @Override
    public void addCustomerPayment(String customerId, PR_APayment pr_A_payment, ResourceRequestCallback<PR_APayment> callback) {
        final WeakReference<ResourceRequest<PR_APayment>> addCustomerPaymentRequest = new WeakReference<ResourceRequest<PR_APayment>>(new ResourceRequest<>().body(pr_A_payment).id(customerId));
        final WeakReference<ResourceRequestCallback<PR_APayment>> addCustomerPaymentCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                PaymentInteractorImpl.super.doGetResource(addCustomerPaymentRequest.get(),
                        addCustomerPaymentCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.createCustomerPayment(addCustomerPaymentRequest.get());
                            }
                        });


            }
        });
    }

    // -------------------------------------------------------------------------------------------
    // CUSTOMER PAYMENT UPDATE
    // -------------------------------------------------------------------------------------------

    @Override
    public void updateCustomerPayment(String customerId, String paymentId, PR_APayment pr_A_payment, final ResourceRequestCallback<PR_APayment> callback) {
        final ResourceRequest<PR_APayment> updateCustomerPaymentRequest = new ResourceRequest<>().body(pr_A_payment).id(customerId).secondLevelId(paymentId);

        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                PaymentInteractorImpl.super.doGetResource(updateCustomerPaymentRequest,
                        callback,
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateCustomerPayment(updateCustomerPaymentRequest);
                            }
                        });


            }
        });
    }


    // -------------------------------------------------------------------------------------------
    // CUSTOMER PAYMENT TRANSACTION CREATION
    // -------------------------------------------------------------------------------------------

    @Override
    public void addTransactionToCustomerPayment(String customerId,
                                                String paymentId,
                                                PR_APaymentTransaction pr_aPaymentTransaction,
                                                final ResourceRequestCallback<PR_APaymentTransaction> callback) {
        final WeakReference<ResourceRequest<PR_APaymentTransaction>> addCustomerPaymentTransactionRequest = new WeakReference<ResourceRequest<PR_APaymentTransaction>>(new ResourceRequest<>().body(pr_aPaymentTransaction).id(customerId).secondLevelId(paymentId));
        final WeakReference<ResourceRequestCallback<PR_APaymentTransaction>> addCustomerPaymentTransactionCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {

                PaymentInteractorImpl.super.doGetResource(addCustomerPaymentTransactionRequest.get(),
                        addCustomerPaymentTransactionCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.addTransactionToCustomerPayment(addCustomerPaymentTransactionRequest.get());
                            }
                        });


            }
        });

    }

    // -------------------------------------------------------------------------------------------
    // CUSTOMER PAYMENT TRANSACTION UPDATE
    // -------------------------------------------------------------------------------------------


    @Override
    public void updateCustomerTransactionPayment(String customerId,
                                                 String paymentId,
                                                 String transactionId,
                                                 PR_APaymentTransaction pr_aPaymentTransaction,
                                                 final ResourceRequestCallback<PR_APaymentTransaction> callback) {
        final WeakReference<ResourceRequest<PR_APaymentTransaction>> updateCustomerPaymentTransactionRequest = new WeakReference<ResourceRequest<PR_APaymentTransaction>>(new ResourceRequest<>().
                body(pr_aPaymentTransaction)
                .id(customerId)
                .secondLevelId(paymentId)
                .thirdLevelId(transactionId));
        final WeakReference<ResourceRequestCallback<PR_APaymentTransaction>> updateCustomerPaymentTransactionCallback = new WeakReference<>(callback);
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                PaymentInteractorImpl.super.doGetResource(updateCustomerPaymentTransactionRequest.get(),
                        updateCustomerPaymentTransactionCallback.get(),
                        new ResourceGetter() {
                            @Override
                            public ResourceResponse getResource(ResourceRequest resourceRequest) {
                                return repository.updateCustomerPaymentTransaction(updateCustomerPaymentTransactionRequest.get());
                            }
                        });


            }
        });

    }


}