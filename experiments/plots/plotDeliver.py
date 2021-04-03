import matplotlib.pyplot as plt
import numpy as np

# DATA
# entry; bytes; gas
# 0;1;33238
# 1;2;33366
# 2;4;36550
# 3;8;36806
# 4;16;34734
# 5;32;35758
# 6;64;40862
# 7;128;46031
# 8;256;53848

# Create the range.
x_axis_label = [1, 2, 4, 8, 16, 32, 64, 128, 256]
x_axis_tick_labels = [1, '', '', '', 16, 32, 64, 128, 256]
y_replicate = [number / 10000 for number in [33238, 33366, 36550, 36806, 34734, 35758, 40862, 46031, 53848]]

# Create the figure
fig = plt.figure(1, figsize=(5, 3.5))
ax = fig.add_subplot()
a = np.arange(len(x_axis_label))

# Add the plots
ax.plot(x_axis_label, y_replicate, 'b-x', label='Always with replica (BL2)')

# Set the x axis
ax.xaxis.set_ticks(x_axis_label)
ax.xaxis.set_ticklabels(x_axis_tick_labels)
ax.legend()

# Add labels and show the plot
plt.xlabel("Value size (in Bytes)")
plt.ylabel("Gas cost (x10.000 Gas)")
plt.savefig("experiment_deliver.svg")
plt.show()
