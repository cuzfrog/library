package cuz.my.psmm;

import cuz.my.psmm.accessories.NotThreadSafe;
import cuz.my.psmm.data.Data;

//assume Factory is run by single thread.
@NotThreadSafe
final class GeneralPsmmFactory implements PsmmFactory {

	private Data data;
	private Module module;
	private Messages.Type type;

	@Override
	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public <T> TMessage<T> commit(TMessage<T> messageBeingWrapped) {
		// TODO Auto-generated method stub

		TMessage<T> newMessage = module.createMessage(type, messageBeingWrapped, data);

		// once a new message has been created, delete this.data reference
		// for safety.
		data = null;

		return newMessage;

	}

	@Override
	public <T> void set(String key, T datum) {
		// TODO Auto-generated method stub
		data.set(key, datum);
	}

	@Override
	public <T> PsmmFactory assemble(Module module, Messages.Type type) {
		this.type = type;
		module.setup(this);
		return this;
	}

}
