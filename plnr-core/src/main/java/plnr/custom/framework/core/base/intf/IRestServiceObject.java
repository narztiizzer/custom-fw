package plnr.custom.framework.core.base.intf;

import plnr.custom.framework.core.base.enumulation.RequestMethod;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;

public interface IRestServiceObject {
    public void fetch(RequestMethod method, IRestServiceObjectDelegate handler, UpdatePolicy policy);

    public void fetch(RequestMethod method, UpdatePolicy policy);

    public void fetch(IRestServiceObjectDelegate handler, UpdatePolicy policy);

    public void fetch(UpdatePolicy policy);

    public void push(RequestMethod method, IRestServiceObjectDelegate handler);

    public void push(RequestMethod method);

    public void push(IRestServiceObjectDelegate handler);

    public void push();

}
