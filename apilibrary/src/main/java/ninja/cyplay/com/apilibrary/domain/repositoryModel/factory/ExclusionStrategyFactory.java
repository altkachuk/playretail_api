package ninja.cyplay.com.apilibrary.domain.repositoryModel.factory;

import com.google.gson.ExclusionStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romainlebouc on 05/12/2016.
 */

public class ExclusionStrategyFactory {


    private final List<ExclusionStrategy> exclusionStrategies = new ArrayList<>();
    private static ExclusionStrategyFactory INSTANCE = null;

    // Private __ctor__
    private ExclusionStrategyFactory() { }

    public static ExclusionStrategyFactory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ExclusionStrategyFactory();
        return INSTANCE;
    }

    public void register(ExclusionStrategy exclusionStrategy) {
        exclusionStrategies.add(exclusionStrategy);
    }

    public List<ExclusionStrategy> getExclusionStrategies() {
        return exclusionStrategies;
    }


}
