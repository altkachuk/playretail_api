package ninja.cyplay.com.playretail_api.ui.fragment;

/**
 * Created by damien on 07/05/15.
 */
public class CustomerPlaylistFragment_old_design extends OpinionBaseFragment {
//
//    @Inject
//    public Bus bus;
//
//    @InjectView(R.id.playlist_navigation_options)
//    MultiStateToggleButton navigationOptions;
//
//    @InjectView(R.id.recycler_view)
//    RecyclerView recyclerView;
//
//    private ProductRecyclerAdapter      adapter;
//    private RecyclerView.LayoutManager  layoutManager;
//
//    private Recommendation recommendation;
//
//    List<Product> products = new ArrayList<Product>();
//
//    // -------------------------------------------------------------------------------------------
//    //                                      Lifecycle
//    // -------------------------------------------------------------------------------------------
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_customer_playlist, container, false);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        bus.register(this);
//        adapter = new ProductRecyclerAdapter(getActivity(), products);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initRecycler();
//        loadRecommendation();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        bus.unregister(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (adapter != null)
//            adapter.notifyDataSetChanged();
//    }
//
//    // -------------------------------------------------------------------------------------------
//    //                                      Method(s)
//    // -------------------------------------------------------------------------------------------
//
//    private void initRecycler() {
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void loadRecommendation() {
//        recommendation = ((CustomerActivity)getActivity()).recommendation;
//        if (recommendation != null) {
//            loadProducts();
//        }
//    }
//
//    private void loadProducts() {
//        if (this.recommendation != null) {
//            this.products.clear();
//            this.products.addAll(recommendation.rl);
//            adapter.notifyDataSetChanged();
//            createNavigationOptions();
//        }
//    }
//
//    public void createNavigationOptions() {
//        List<String> titles = new ArrayList<String>();
//        titles.add(getResources().getString(R.string.all));
//        if (recommendation != null
//                && recommendation.segments != null)
//            for (int i = 0; i < recommendation.segments.size(); i++)
//                titles.add(recommendation.segments.get(i).seg_lab);
//
//        navigationOptions.setElements(titles);
//        navigationOptions.setValue(0);
//        navigationOptions.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
//            @Override
//            public void onValueChanged(int assignedValue) {
//                updatefilteredProducts();
//            }
//        });
//    }
//
//    private List<Product> getFilterProducts(List<Product> productsToFilter, int filter) {
//        if (filter == 0) // 0 = All = no filter
//            return productsToFilter;
//
//        List<Product> filteredProducts = new ArrayList<Product>();
//
//        Segment selectedSegment = null;
//        if (recommendation != null
//                && recommendation.segments != null
//                && recommendation.segments.size() > (filter - 1))
//            selectedSegment = recommendation.segments.get(filter - 1);
//
//        if (selectedSegment != null)
//            for (Product product : productsToFilter) {
//                if (product.getCat() != null && selectedSegment.seg_code.toUpperCase().equals(product.getCat().toUpperCase()))
//                    filteredProducts.add(product);
//            }
//        return filteredProducts;
//    }
//
//    private void updatefilteredProducts() {
//        if (products != null) {
//            adapter.setProducts(getFilterProducts(products, navigationOptions.getValue()));
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//
//    // -------------------------------------------------------------------------------------------
//    //                                      View Impl
//    // -------------------------------------------------------------------------------------------
//
//    @Override
//    public void onLikeSuccess() {
//        if (adapter != null)
//            adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onDisLikeSuccess() {
//        if (adapter != null)
//            adapter.notifyDataSetChanged();
//    }
//
//    // -------------------------------------------------------------------------------------------
//    //                                       Event(s)
//    // -------------------------------------------------------------------------------------------
//
//    @Subscribe
//    public void getCustomerInfoEvent(GetCustomerInfoEvent event) {
//        loadRecommendation();
//    }
//
//    @Subscribe
//    public void onTabSelect(PlaylistTabEvent customerInfoTabEvent) {
//        PageView pageView = pagesViews.createPageView(className);
//        List<String> ids = new ArrayList<>();
//        if (products != null)
//            for (int i = 0 ; i < products.size() ; i++)
//                ids.add(String.valueOf(products.get(i).getSkid() != null ? products.get(i).getSkid() : ""));
//        pageView.setPlaylist(ids);
//        pagesViews.addPageView(pageView);
//    }

}
