/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa
 * y Milton Pividori.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package frsf.cidisi.faia.agent;

import frsf.cidisi.faia.environment.Environment;

/**
 * This class is used to inform the agent about perceptions of the
 * real world.
 */
public abstract class Perception {

    public Perception() {
    }

    public Perception(Agent agent, Environment environment) {
        this.initPerception(agent, environment);
    }

    public abstract void initPerception(Agent agent, Environment environment);


}
