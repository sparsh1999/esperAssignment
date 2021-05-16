
ESPER ASSIGNMENT

This Repo contains the source code for an App, which which makes an API call to fetch a list of
Mobile phones and their features from the following URL. Some exclusions might also be present.

Entites
1. Selection , has featureId and optionId column
2. Feature   , has meta data of feature
3. Options   , has meta data of options and a foreign key of feature
4. Exclusions, has two column names selection1Id and selection2Id which describes which two selection
               cannot occur at same time, both are foreign key to selection table

UI
Home Activity
1. Tablayout    , to display all features (names).
2. Recyclerview , to show options for the selection feature (through tab)
3. SaveButton   , which getActivated or visible when (no of user selection) == (total no of features)
                  i.e one option from each feature has been selection

Result Activity
1. Listview     , to show all the selected featureId and OptionId

IMPORTANT, DRIVER CLASS TO MANAGE SELECTIONS

SelectionManager , It is solely responsible for managing user selections, a selection is (featureId, optionId)
1. adding selections
2. removing selections
3. adding Exclusions
4. removing Exclusions
5. giving All Selectable options for a featureId which do not have any conflict over any other selection
6. giving which optionId is selected for a featureId (currently from one feature only 1 option can be selected)
7. Notifying to listener whenever there is a change (addition/removal) from the selection Set.
8. VARIABLES
8.1 Set<Selection> selections  , to store user selections (featureId, optionId)
8.2 exclusionMap               , a dict where each key is a selection and each value is a list of selections
                                 which are not available due to this selection(key)
                                 Ex, say if some exclusions are [ [{1,4},{6,10}], [{1,4},{11, 15}] ]
                                 then when {1,4} is selected then , exclusionMap contains {{1,4}:[{6,10},{11, 15}]}
                                 so when you visit feature 6, then option 10 will be not clickable and dimmed
                                 and when you visit feature11 , then option 15 will be not clickable and dimmed
SelectionChangeListener
HomeActivity, registers with SelectionManager by implementing this listener interface
to observe any changes in the selection set of selectionManager, selectionChanged() is invoked in such cases
based on this saveButton in homeActivity is toggled

Adapter

OptionAdapter
1. shows all options for a featureId
2. the options which are not available, due to any conflict with other selections ,are dimmed and set non clickable
3. user can select all other options which does not have point2 or are available.
4. calls SelectionManager internally, to notify about the selection or deletion.
5. VARIABLES
5.1 selectedOptionId,       it contains the optionId which is selected in the given list of options
                            for a featureId or if nothing selected then -1
5.2 Set<Integer> exclusions contains a set of optionId which are not available due to conflict with other selections.


Responses
temp classes to store server responses

Network
Retrofit classes for Api call.

App
Application class, to initiliaze static data one time, like Database, Retrofit, Picasso , Gson etc.
