//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.plugin.device;

import com.sandpolis.core.instance.state.st.STDocument;
import com.sandpolis.core.instance.state.vst.AbstractSTDomainObject;

public class Device extends AbstractSTDomainObject {

	Device(STDocument document) {
		super(document);
	}

	public String getId() {
		return "";
	}
}
