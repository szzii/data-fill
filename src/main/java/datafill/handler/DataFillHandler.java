package datafill.handler;

import datafill.model.DataFillMetadata;

/**
 * @author szz
 */
public interface DataFillHandler {

    AbstractDataFillHandler fill(DataFillMetadata metadata);

}

