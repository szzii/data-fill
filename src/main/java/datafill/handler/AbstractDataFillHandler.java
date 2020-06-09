package datafill.handler;

import datafill.model.DataFillMetadata;

/**
 * @author szz
 */
public abstract class AbstractDataFillHandler implements DataFillHandler {


    protected  void validation(DataFillMetadata metadata){
        if (metadata == null){
            throw new IllegalArgumentException();
        }else if (metadata.getFillField() == null){
            throw new IllegalArgumentException();
        }else if (metadata.getKey() == null){
            throw new IllegalArgumentException();
        }
    }


}
