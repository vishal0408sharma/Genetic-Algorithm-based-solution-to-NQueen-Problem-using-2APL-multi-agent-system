Genetic Algorithm based solution the famous NQueen problem using Multi agent system - 2APL

2APL is an agent-oriented programming language that facilitates the implementation of multi-agent systems. At the multi-agent level, it provides programming constructs to specify a multi-agent system in terms of a set of individual agents and a set of environments in which they can perform actions. At the individual agent level, it provides programming constructs to implement cognitive agents based on the BDI architecture. In particular, it provides programming constructs to implement an agent’s beliefs, goals, plans, actions (such as belief updates, external actions, or communication actions), events, and a set of rules through which the agent can decide which actions to perform. 2APL is a modular programming language allowing the encapsulation of cognitive components in modules. 2APL supports the implementation of both reactive and pro-active agents.


The 2APL platform can be invoked from the command prompt using the following command - <br>
java -jar 2apl.jar

To load an example multi-agent system, one should perform the following steps:

1. Select from the menu file → open, or alternatively the open button located on the toolbar, and
an open file dialog appears.

2. Browse to the directory 2apl_implementation/ and select the file named nqueenGA.mas <br>
The file with the .mas extension specifies the multi-agent system by indicating which agents should be created, which .2apl files initialize the agents, which environments they can access and which .jar 



Files -

agent.2apl - agent 2apl file

NQueenGAGUI.java, GAGUI.java, ChessBoard.java - environment (NQueenGAGUI.java being the main class)

env.jar - jar file for the environment

nqueenGA.mas - multi agent system file specifying the agents and the jar file for the environment

